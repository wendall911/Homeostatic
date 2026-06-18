# Homeostatic — Project Context

## What This Is
A temperature and thirst mod grounded in real-world science. Built as a ground-up replacement for Tough As Nails with a proper scientific foundation. Environment temperature is based on wet-bulb globe temperature (WBGT), biome temperatures are overridden for realism, and thirst is affected by environment temperature, body temperature, and activity. Body temperature is regulated through armor modifications and heat sources. See README.md for full design rationale.

## Project Structure
Multi-loader: `Common/` + `NeoForge/` + `Fabric/`

## Branch Convention
| Branch | Modloaders        |
|--------|-------------------|
| 1.18.2 | Forge + Fabric    |
| 1.19.2 | Forge + Fabric    |
| 1.20.1 | Forge + Fabric    |
| 1.21.1 | NeoForge + Fabric |
| 26.1   | NeoForge + Fabric |

Maintained: 1.20.1, 1.21.1, 26.1

## Embedded Sub-Project: ClimateSettings
`ClimateSettings/` is a sub-project inside this repository. It provides the
temperature registry — an API layer that other mods can depend on without requiring the full Homeostatic mod. HomeostaticSeasons is the primary consumer.

ClimateSettings is built and published to Maven from within this repo, then re-consumed by Homeostatic itself as a jarJar/include dependency.

**Planned migration:** ClimateSettings should be extracted to a standalone top-level library (like WhiteNoise and Handbook). Currently, updates to ClimateSettings require releasing Homeostatic first, then HomeostaticSeasons (which depends on ClimateSettings), then Homeostatic again if HomeostaticSeasons version changed — a circular update chain.  This migration is under consideration; see project-context notes before modifying ClimateSettings or HomeostaticSeasons.

## Dependencies
- ClimateSettings (jarJar/include — embedded sub-project, see above)
- WhiteNoise (jarJar/include)
- Handbook (jarJar/include)
- HomeostaticSeasons (optional runtime integration)
- Serene Seasons (optional runtime integration)

## Distribution
Side: both (clientRequired = true, serverRequired = true)

## Release Process
Follow the standard wendall911 release process in `../docs/minecraft/MINECRAFT_DEVELOPMENT_NOTES.md`.
