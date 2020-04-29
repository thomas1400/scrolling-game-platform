## Description

The reset button incorrectly loads the level selector screen instead of the game screen.

## Expected Behavior

The reset button reloads the current level.

## Current Behavior

The reset button loads the current level, then after a delay of 3 seconds, loads the level selector screen.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. Play a level in any game.
 2. Press the reset button.
 3. Wait 3 seconds.

## Hypothesis for Fixing the Bug

The code to reset the level incorrectly also calls the transition back to the level selection screen. 
Separating this functionality into two methods would solve the problem.