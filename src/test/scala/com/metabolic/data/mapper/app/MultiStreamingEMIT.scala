package com.metabolic.data.mapper.app

import com.holdenkarau.spark.testing.{DataFrameSuiteBase, SharedSparkContext}
import com.metabolic.data.RegionedTest
import com.metabolic.data.core.domain.{Defaults, Environment}
import com.metabolic.data.mapper.domain.Config
import com.metabolic.data.mapper.domain.io.{EngineMode, FileSink, FileSource, IOFormat}
import com.metabolic.data.mapper.domain.ops.SQLStatmentMapping
import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SaveMode}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

class MultiStreamingEMIT extends AnyFunSuite
  with DataFrameSuiteBase
  with SharedSparkContext
  with BeforeAndAfterAll
  with RegionedTest {

  override def conf: SparkConf = super.conf
    .set("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
    .set("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")


  val firstBatchData = Seq(
    Row("A", "a", 2022, 2, 5, "2022-02-05"),
    Row("B", "b", 2022, 2, 4, "2022-02-04"),
    Row("C", "c", 2022, 2, 3, "2022-02-03"),
    Row("D", "d", 2022, 2, 2, "2022-02-02")
  )

  val secondBatchData = Seq(
    Row("E", "e", 2022, 2, 1, "2022-02-01"),
    Row("F", "f", 2022, 1, 5, "2022-01-05"),
    Row("G", "g", 2021, 2, 2, "2021-02-02"),
    Row("H", "h", 2020, 2, 5, "2020-02-05")
  )

  val inputSchema = List(
    StructField("name", StringType, true),
    StructField("data", StringType, true),
    StructField("yyyy", IntegerType, true),
    StructField("mm", IntegerType, true),
    StructField("dd", IntegerType, true),
    StructField("date", StringType, true),
  )

  val checkpoints_path = "src/test/tmp/checkpoints"
  val input_path = "src/test/tmp/delta/letters"
  val output_path = "src/test/tmp/delta/all_letters"


  test("Single Job") {

    val sqlCtx = sqlContext

    val firstInput = spark.createDataFrame(
      spark.sparkContext.parallelize(firstBatchData),
      StructType(inputSchema)
    )

    firstInput
      .write
      .format("delta")
      .save(input_path)

    val source = FileSource(input_path, "letters", IOFormat.DELTA)
    val sql = "SELECT * FROM letters"

    val singleStreamingJobConfig = Config(
      "My Streaming Test",
      List(source),
      List(SQLStatmentMapping(sql)),
      FileSink("all_letters", output_path, SaveMode.Overwrite, IOFormat.DELTA),
      Defaults(ConfigFactory.load()), Environment("", EngineMode.Stream, checkpoints_path, false, "test", "", Option.empty)
    )

    MetabolicApp()
      .transformAll(List(singleStreamingJobConfig))(region, spark)


  }

}
