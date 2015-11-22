# sysadmincraft

[![Build Status](https://travis-ci.org/simplyianm/sysadmincraft.svg)](https://travis-ci.org/simplyianm/sysadmincraft)

Admin your server in Minecraft!

SysAdmincraft is a way to monitor your server through a fun interface. To kill a process, just kill a monster! Memory management is easy -- you can visually see what processes are taking the most memory on your server. No more ps, top, uname -a, etc! Just connect to a Minecraft server and start managing everything!

## Running

* Run `sbt test assembly` to create the plugin.
* `cd` to the `server/` directory.
* Run `bootstrap.sh` to set up the Minecraft server.
* Run `install.sh` to install the plugin.
* Run `run.sh` to run the server.

## License

ISC
