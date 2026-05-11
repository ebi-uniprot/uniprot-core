# UniProt Core

UniProt Core is a multi-module Java library for UniProt domain models, parsers,
serializers, controlled vocabularies, and related utility code.

The root project is a Maven aggregator. Shared dependency versions, compiler
settings, test configuration, coverage setup, formatting, and deployment
metadata are defined in [`pom.xml`](pom.xml).

## Requirements

- Java 17
- Maven 3.8 or newer

## Modules

| Module | Purpose |
| --- | --- |
| `core-common` | Shared low-level utilities used by the rest of the project. |
| `core-domain` | Core UniProt domain objects, builders, value types, and supporting model code. |
| `core-parser` | General parser APIs and mappers for FASTA, GFF, TSV, and related workflows. |
| `ff-parser` | UniProt flat file parsing support, including ANTLR-generated parser code. |
| `json-parser` | Jackson-based JSON serialization and deserialization for UniProt domain objects. |
| `xml-parser` | XML parsing, writing, and JAXB/XSD-generated XML model support. |
| `controlled-vocabulary` | Controlled vocabulary repositories and readers for UniProt reference data. |
| `core-domain-utils` | Higher-level helper utilities built on top of `core-domain` and controlled vocabularies. |
| `tools-entry-scorer` | Utilities for scoring UniProtKB entries and their annotations. |
| `jacoco-aggregate-report` | Aggregated JaCoCo coverage report module. |

## Build

Build and verify every module:

```bash
mvn clean verify
```

Install all artifacts into your local Maven repository:

```bash
mvn clean install
```

Build or test a single module with its required reactor dependencies:

```bash
mvn -pl core-domain -am test
```

Skip tests when only packaging artifacts:

```bash
mvn package -DskipTests
```

## Tests

Run the unit test suite:

```bash
mvn test
```

Run the full verification lifecycle, including integration-test phases where
configured:

```bash
mvn verify
```

Useful test properties:

```bash
mvn test -DskipITs=true
mvn verify -DskipUTs=true
```

Test reports are written under each module's `target/surefire-reports` or
`target/failsafe-reports` directory. Coverage data is produced with JaCoCo and
aggregated by `jacoco-aggregate-report`.

## Formatting

The `format` Maven profile applies Spotless formatting with Google Java Format
using AOSP style:

```bash
mvn -Pformat compile
```

To run a build without the Spotless execution bound by that profile, use the
`no-spotless` profile where needed:

```bash
mvn -Pno-spotless verify
```

## Generated Sources

Some modules generate sources as part of the Maven lifecycle:

- `ff-parser` generates ANTLR parser sources from flat file grammar inputs.
- `xml-parser` generates JAXB sources from XSD resources.

Do not edit generated files directly. Change the grammar, schema, or source
configuration and then rebuild the relevant module.

## Artifacts

The project uses the `org.uniprot` Maven group. The parent artifact is:

```xml
<groupId>org.uniprot</groupId>
<artifactId>core-parent</artifactId>
<version>1.0.39-SNAPSHOT</version>
```

Individual modules are published as separate artifacts, for example:

```xml
<dependency>
  <groupId>org.uniprot</groupId>
  <artifactId>core-domain</artifactId>
  <version>1.0.39-SNAPSHOT</version>
</dependency>
```

Snapshot and release deployment repositories are configured in the parent POM.
Deployment requires the appropriate UniProt Artifactory credentials and Maven
settings.

## CI

GitLab CI builds, tests, publishes coverage artifacts, runs Sonar analysis, and
deploys from the main branch. The CI image uses Maven with OpenJDK 17 and relies
on project-specific Maven settings for deployment.

There is also a GitHub workflow that applies Spotless formatting on pull
requests and pushes to the configured branch.

## Repository Layout

```text
.
├── controlled-vocabulary/
├── core-common/
├── core-domain/
├── core-domain-utils/
├── core-parser/
├── ff-parser/
├── jacoco-aggregate-report/
├── json-parser/
├── tools-entry-scorer/
├── xml-parser/
└── pom.xml
```

## Contributing

- Keep changes in the module that owns the behavior.
- Prefer existing domain builders, parser abstractions, and utility classes over
  adding parallel implementations.
- Add or update tests in the same module as the changed behavior.
- Rebuild modules that generate sources after changing grammars, schemas, or
  generated-source configuration.
- Run `mvn test` or a narrower `mvn -pl <module> -am test` before opening a
  change.

