# Getting Started
## Introduction
This is a tutorial on how and what commands to use to edit code in this repository. Please take a few seconds to read this carefully so that we can avoid unnecessary code being pushed into the project.

## Requirements
We will be using Git for version control. Search for tutorials on how to install Git into your machine if you don't have it yet. The following instructions will assume Git has been installed already.

## Without Git
It is possible to do Git commands directly on DevOps. Go to the dev branch by clicking main which shows a drop-down menu and click dev. Click on the *.java* file you want to work on and the Edit button will let you modify the text on the website.

However, **please make sure your code works when you run it using DogFight.java before clicking commit.** This is so that other people copying your code into their machine won't run into any problems when they run their own tests because you failed to debug your code.

The reason Git is preferred is because you can immediately push your working code on the same environment (IDE) and save the hassle of copy & pasting code manually.

## Git Clone
There are 2 ways to clone the DogFight repository into your machine. The simpler way is to use Clone in VSCode or any IDE you prefer to use for Git.

Click main at the top to open the drop-down menu, and click dev. 
> The main branch is here as a safeguard, so we will use the dev branch to push our codes into. Once it's been decided that the code in dev so far is good, that's when we will merge dev to main.

In the dev branch, click Clone. Feel free to use the command line option, but this tutorial will use Clone in VSCode. Clicking this will open up VSCode.
> If you click the arrow next to Clone in VSCode you will find options for Eclipse PyCharm as well.

You will be asked to specify where you want the clone to take place. **THIS IS IMPORTANT.** Find your *Comp2800\*\** project file you use for COMP 2800. Open *src* and click Select as Repository Destination (you should see your codes\*\*280 folder in the same location)
> If you already have the *DogFight* folder inside *src*, rename it to something else like *DogFight-Old* as the clone has the same name, and we will need to keep your code to copy & paste in a bit. 

You're now ready to edit your code. When you select *src* as your directory, the clone already has the package name *DogFight* so you can immediately run the program in Eclipse.

## Editing Code
You can run *DogFight.java* as you normally would by opening the project file in Eclipse and finding the *DogFight* package (it's in the same level as your *codes\*\*280* package). Right-click *DogFight.java* and run as java application.
This is also where you can copy & paste your code you already worked on before cloning. 
> If you've used the .java files I provided on Discord, you should be able to just copy & paste the text from your .java file from the renamed package (i.e., DogFight-Old) and then pasting it into the corresponding cloned .java file. **Please try not to remove any lines of code**. Just paste your new code on the empty lines.
> You can also create a new .java file in the DogFight package (you will need to use a name other than "Environment," "Fighter," etc.) and later on push that onto the remote repository.

## Save your code to the dev branch
After you've written and tested your new code, add your code into the repository on DevOps. We will use Git commands in the terminal.
Make sure you're in the *...\\Comp2800\*\*\\src\\DogFight* directory.
#### Check if the package was updated while you were editing.
`git pull` updates your files if there has been any new changes since you last cloned/pulled. It will notify you if there are any conflicts between the new change and your new code.

#### Use git add to tell Git to track your changes. 
A good practice is to only add files that are related, so that we can add comments that can be understood easily when we commit.

`git add .` tracks everything in the "DogFight" package.

`git add Environment.java` tracks the Environment.java. You can add as many as you want manually.

#### Use git commit to prepare the push.

`git commit -m "insert commit comment"` please enter a comment that's short but easily understood by the reader, such as "Added Plane class into Fighter class to load the plane object"

#### Make sure you're in the "dev" branch.
`git branch --track dev origin` moves to (if it exists) or creates (if it doesn't exist) the dev branch. You will be pushing your code onto this branch.
`git branch -v` prints the branches that are currently on your local repository.
`git switch dev` switches your "head" (the branch you are working on) to dev.

#### Making the push from your machine
`git push -u origin dev` will actually send your code to the repository and add/replace/delete any modifications you made to the file(s)
The `-u` flag will allow you to simply use `git push` from now on without the need to specify `-u origin dev`.


# Build and Test
TODO: Describe and show how to build your code and run the tests. 

# Contribute
If you want to learn more about creating good readme files then refer the following [guidelines](https://docs.microsoft.com/en-us/azure/devops/repos/git/create-a-readme?view=azure-devops). You can also seek inspiration from the below readme files:
- [ASP.NET Core](https://github.com/aspnet/Home)
- [Visual Studio Code](https://github.com/Microsoft/vscode)
- [Chakra Core](https://github.com/Microsoft/ChakraCore)