# Reporting Issues

Before reporting an issue, search to see if anyone has the same issue. Make sure to check closed issues as well as there is a chance one of them has the solution.

Provide clear steps to reproduce the issue, especially in the case of crashes. "_It crashed_", is not useful, "_crash loading configuration file_" is useful, and "crash when configuration filename contains spaces" is even better. If the bug happens on a server, make sure to test in single player, and to test with a normal Forge server if using Sponge Forge.

## Versions

Always test with the latest versions of all relevant mods; chances are the bug you are reporting has been fixed in a later version of Homeostatic or Forge. We do not support Minecraft versions before 1.12.2. For older versions, please use InGameInfo-XML.

## Crashes

For crashes, always provide a crash report. Crash reports should be added using an external site such as https://pastebin.com or https://gist.github.com and linked in the issue to avoid clutter.

## Mod List

Try to minimize the list of mods needed to reproduce the bug. Performance enhancing mods and core mods can be expecially problematic due to their changes to the base Minecraft code, so especially try to remove them to see if it is the cause and provide that information in the report.

## Pull Requests

Always talk to the developers first before working on pull requests, such as on [the Homeostatic Discord](https://discord.gg/7tQCF4q). Pull requests will only be accepted if they contribute something meaningful and do not hinder maintainability. Furthermore pull requests must be tested and ensure to not break anything.

An exception to this rule is translation pull requests, which we generally allow without previous discussion. Please do not translate using an automatic translator such as Google Translate as those translations tend to be filled with errors or use the wrong context.
