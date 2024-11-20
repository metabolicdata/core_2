import sbt.*

object Dependencies {
  object Versions {
    val scala = "2.12.20"
    val spark = "3.3.2"
    val awsSdk = "1.12.682"
    val testContainers = "0.40.12"
    val jackson = "2.13.0"
    val scalaTest = "3.2.9"
    val snappyJava = "1.1.8.2"
    val hadoopAws = "3.3.4"
    val log4jScala = "12.0"
    val deltaCore = "2.3.0"
    val icebergRuntime = "1.6.1"
    val liftJson = "3.5.0"
    val scalaUri = "1.4.10"
    val snakeYaml = "1.29"
    val scalajHttp = "2.4.2"
    val playJson = "2.9.4"
    val jsonSerde = "1.3.9-e.10"
    val antlrRuntime = "4.8"
    val scalaXml = "1.3.0"
    val mysqlConnector = "5.1.49"
    val mockitoCore = "2.21.0"
    val hadoopAwsTest = "3.3.4"
    val kafkaClients = "3.3.2"
    val config = "1.4.0"
  }

  val sparkDependencies = Seq(
    "org.apache.spark"              %% "spark-sql"                   % Versions.spark % Provided,
    "org.apache.spark"              %% "spark-sql-kafka-0-10"        % Versions.spark,
    "io.delta"                               %% "delta-core"                  % Versions.deltaCore,
    "org.apache.iceberg"            %% "iceberg-spark-runtime-3.3"   % Versions.icebergRuntime,
    "org.apache.iceberg"            %  "iceberg-aws-bundle"          % Versions.icebergRuntime
  )

  val awsDependencies = Seq(
    "com.amazonaws"                 % "aws-java-sdk-s3"              % Versions.awsSdk    % Provided,
    "com.amazonaws"                 % "aws-java-sdk-secretsmanager"  % Versions.awsSdk    % Provided,
    "com.amazonaws"                 % "aws-java-sdk-glue"            % Versions.awsSdk    % Provided,
    "com.amazonaws"                 % "aws-java-sdk-kinesis"         % Versions.awsSdk    % Provided,
    "com.amazonaws"                 % "aws-java-sdk-athena"          % Versions.awsSdk    % Provided
  )

  val loggingDependencies = Seq(
    "org.apache.logging.log4j"      %% "log4j-api-scala"             % Versions.log4jScala
  )

  val commonDependencies = Seq(
    "org.apache.kafka"              %  "kafka-clients"               % Versions.kafkaClients,
    "com.typesafe"                  %  "config"                      % Versions.config,
    "net.liftweb"                   %% "lift-json"                   % Versions.liftJson,
    "io.lemonlabs"                  %% "scala-uri"                   % Versions.scalaUri,
    "org.yaml"                      %  "snakeyaml"                   % Versions.snakeYaml,
    "org.scalaj"                    %% "scalaj-http"                 % Versions.scalajHttp,
    "com.typesafe.play"             %% "play-json"                   % Versions.playJson,
    "io.starburst.openx.data"       %  "json-serde"                  % Versions.jsonSerde
  )

  val testDependencies = Seq(
    "org.scalatest"                 %% "scalatest"                   % Versions.scalaTest       % Test,
    "com.holdenkarau"               %% "spark-testing-base"          % s"${Versions.spark}_1.4.7"  % Test,
    "org.xerial.snappy"             %  "snappy-java"                 % Versions.snappyJava      % Test,
    "com.dimafeng"                  %% "testcontainers-scala-scalatest" % Versions.testContainers % Test,
    "com.dimafeng"                  %% "testcontainers-scala-mysql"     % Versions.testContainers % Test,
    "com.dimafeng"                  %% "testcontainers-scala-kafka"     % Versions.testContainers % Test,
    "com.dimafeng"                  %% "testcontainers-scala-localstack" % Versions.testContainers % Test,
    "org.apache.hadoop"             %  "hadoop-aws"                  % Versions.hadoopAwsTest   % Test,
    "mysql"                         %  "mysql-connector-java"        % Versions.mysqlConnector  % Test,
    "org.mockito"                   %  "mockito-core"                % Versions.mockitoCore     % Test
  )

  val dependencyOverridesSettings = Seq(
    "com.fasterxml.jackson.module"  %% "jackson-module-scala"        % Versions.jackson,
    "com.fasterxml.jackson.core"    %  "jackson-databind"            % Versions.jackson,
    "org.antlr"                     %  "antlr4-runtime"              % Versions.antlrRuntime,
    "org.scala-lang.modules"        %% "scala-xml"                   % Versions.scalaXml
  )
}