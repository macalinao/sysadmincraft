# sysadmincraft

[![Build Status](https://travis-ci.org/simplyianm/sysadmincraft.svg)](https://travis-ci.org/simplyianm/sysadmincraft)

[View on YouTube][youtube]

Admin your server in Minecraft!

SysAdmincraft is a way to monitor your server through a fun interface. To kill a process, just kill a monster! Memory management is easy -- you can visually see what processes are taking the most memory on your server. No more ps, top, uname -a, etc! Just connect to a Minecraft server and start managing everything!

The plugin also exposes a few commands, like:

* `/pgrep <name>` -- Takes you to the column representing a process
* `/ps` -- Lists all running processes belonging to your user
* `/top` -- Takes you to the "top" of the map and shows `uname -a` and `uptime` output (and memory info if you're on a Mac)

*Note: This is obviously a joke and is not meant to be used in production (or development, for that matter). A judge at the hackathon asked us how we were planning on monetizing it!*

## Running

* Run `sbt test assembly` to create the plugin.
* `cd` to the `server/` directory.
* Run `bootstrap.sh` to set up the Minecraft server.
* Run `install.sh` to install the plugin.
* Run `run.sh` to run the server.

## License

ISC

[youtube]: https://youtu.be/rD9yJ9MHWzo
