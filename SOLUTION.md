Author: Samuel Jackson

# Building and running the program
If you have gradle and java installed, building will be as simple as running the `install.sh` file present in the topmost directory. This will create a simlink called `./mem` that can be called to easily access the tool as per specifications. Inorder to access it by the name of memallook itself, you'll need to link to it elsewhere since the name is already used here.

# Design decisions
## Where to save the memory model
In order to have the memory model percist across calls, it needs to be saved to the disk somewhere that is both safe and also doesn't interfere with the user's setup. I chose to use the .config file since it is for things like this. It could prove to be a problem if the requested sizes became huge at which point I would try to look into using temp files elsewhere on the system but for now this works nicely.

## What datastructure to use to represent memory
Representing memory can be tricky since it's a sparce array with many gaps. It doesn't make sense to track every page of data and it doesn't make sense to keep too much redundant information around. I decided to use a simple list of POJOs to keep track of what was used up and what wasn't. Although it is arguable that this isn't the fastest datastructure to use for this usecase, reading and writing the structure to memory after every use will greatly bottleneck the peak performance anyways.

## Saving the datastructure to memory
Saving a datastrucutre to memory is often quite annoying since you need to find the easiest format to write and read it to. I choose to go with a stright forward approach since it is still in early stages and would be fairly simple to migrate over to a more standard format such as JSON. Here, I just wrote the values in a particular order and read them back in the same order to construct the structure's copy from there.

# Frameworks
Gradle build tool - Since all projects I've worked on have used Amazon's own internal build systems, this was the closest thing I could find to that. It should simplify the building process.
picocli - A handy little framework for creating CLI tools in java. It abstracted away the reading of commandline arguments so the code can focus on how to use them.


# Resources
[This](https://picocli.info/) is the documentation around the pico framework that I used while coding. 
[This](https://docs.gradle.org/current/userguide/distribution_plugin.html#distribution_plugin) documentation discusses how gradle can make an easily runable executable (instead of running the raw gradle or java command each time the the user wants to use the tool).

