# PRESENTATION.md

*Virtually present these plans to me or your assigned UTA with enough justification and detail to convince us your plan is sound. Your presentation can be done synchronously using Zoom, recorded and combined into a single shared video (if possible), or a separate written summary of the plan documents and code files. In any case, each team member should clearly contribute in the presentation.*

*Put any materials specifically for your presentation in the folder `doc/progress2`, either as code, images, UML diagrams, or written text, using Markdown, Javadoc or a wiki page on Gitlab. PowerPoint slides are discouraged because they are completely separate from the project and unlikely to be maintained even if they are added to the repository, but here are some tools to convert Markdown to a slide style format.*

## Start by showing off the features of your running program:
- Run the program through a planned series of steps/interactions (it will look much better if it this demo shows everyone's parts working together as a single program rather than multiple separate demos)
- Show any data files that have been created and describe how they are used within your program (focus on the file content rather than the code that uses it)
- Show a variety of JUnit or TestFX tests (including both happy and sad paths) and discuss how comprehensively they cover the feature(s) and why you chose the values used in the tests
- Everyone should be able to describe how their work relates to specific demoed features as they are shown

## Present what you (or your team overall) learned during this Sprint and the implementation plan for the next Sprint:
- Describe how much of the planned features were completed this Sprint and what helped or impeded progress

individually

- Describe a specific significant event that occurred this Sprint and what was learned from it

push and pull from git

- Describe what worked, what did not, and something specific that is planned for improvement next Sprint

collisions in response to movement (dana)
disappearance of things that are dead

- What features are planned to be completed during the next Sprint (taking into account what was done this Sprint), who will work on each feature, and any concerns that may complicate the plan
- individually



## Sprint 2 Responsibilities:
Make tests!!

### Grant:
- Different Physics Types
    - Parameter Tweaking
- Coins In levels
- Building Vertical Levels
- Level Builder Screen (Very Basic Functionality)
    - GUI based on Simulation
    - Saving Levels
        - Level Validation?
- Levels have a "scroll type" property
- Error Checking for Level and User Loading
- User Saving at end of level and close screen
- Saving New Users
    - Default Values for new Users
- More images for other "Textures"
- Images for coins, mushrooms, etc.
- Restart Game Screen
- (Loading in GIFs)

### Dana:
- Stopping motion when necessary
    - supporting entities on the side/top of ground and brick entities (and any other entites that have the support/bounce type) and 
- Collisions web
    - Detecting Collision Types & Appropriate responses
    - When something becomes "dead" removing it from the screen
    - Implement changing of entity types as a response to being hit
    - Create unit tests for checking this behavior is correct
- Add a direction component to entities and change images based on the direction of motion
- Throw exceptions that aren't runtime exceptions when using reflection
- Add checks for things being the right type and throw exceptions if they aren't
- Creating new entity types that allow the user to gain points and power ups

### Thomas:
- Add CSS styling and create a data-driven layout
- Improve the user selector screen to allow the ability to create new user profiles through the user selector screen, as well as displaying more than the current max of 4 users
- Add a help screen
- Improve the level selector tool to only allow you to select levels you've unlocked, and to make new levels appear as they're unlocked
- Improve the loading screen to show the level you're loading and your lives
- Improve the game screen layout, including dynamically updating score/lives and ending the level on close
- Add the ability to change "texture packs", which restyle the GUI and game elements

### Cayla:
- Implemented properties files and reflection to call methods when receiving input fromt he user
- Implement abstract camera manager / sub classes to allow for different type of movement within a level
- Looking into observers/ listeners for collisions or key detection
- make sure that errors are being properly thrown
- Implement a list into the inputmanager that holds when a key is pressed and removes when the key is released
- End of Level Flag
- Assist Dana after completing tasks