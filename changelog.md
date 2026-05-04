------------------------------------------------------
Version 26.1-r5
------------------------------------------------------
- cleanup datagen for other mods to use

------------------------------------------------------
Version 26.1-r4
------------------------------------------------------
- use getter method in cannibal level component for mixin compat purposes

------------------------------------------------------
Version 26.1-r3
------------------------------------------------------
- update to new strawberrylib

------------------------------------------------------
Version 26.1-r2
------------------------------------------------------
- flesh drops no longer error if the items inside the json files don't exist

------------------------------------------------------
Version 26.1-r1
------------------------------------------------------
- update to 26.1
- add various knife drops to other entities
- add Turkish translation, thanks Hexasan!

------------------------------------------------------
Version 1.21.11-r2
------------------------------------------------------
- update embedded midnightlib to avoid crashing when another mod doesn't have an updated version

------------------------------------------------------
Version 1.21.11-r1
------------------------------------------------------
- update to 1.21.11

------------------------------------------------------
Version 1.21.10-r1
------------------------------------------------------
- update to 1.21.10

------------------------------------------------------
Version 1.21.9-r1
------------------------------------------------------
- update to 1.21.9
- add copper knives

------------------------------------------------------
Version 1.21.8-r2
------------------------------------------------------
- update to latest strawberrylib
- knives can now only be used on yourself when hurt time is 0
- clean up tags

------------------------------------------------------
Version 1.21.8-r1
------------------------------------------------------
- update to 1.21.8

------------------------------------------------------
Version 1.21.6-r1
------------------------------------------------------
- update to 1.21.6

------------------------------------------------------
Version 1.21.5-r2
------------------------------------------------------
- fix pigluttons not rendering held items

------------------------------------------------------
Version 1.21.5-r1
------------------------------------------------------
- update to 1.21.5

------------------------------------------------------
Version 1.21.4-r2
------------------------------------------------------
- fix entities not dropping cooked flesh when applicable

------------------------------------------------------
Version 1.21.4-r1
------------------------------------------------------
- update to 1.21.4

------------------------------------------------------
Version 1.21.2-r1
------------------------------------------------------
- update to 1.21.2
- cannibal night vision now works with shaders and true darkness

------------------------------------------------------
Version 1.21-r1
------------------------------------------------------
- update to 1.21
- merge https://github.com/MoriyaShiine/anthropophagy/pull/12
- update piglutton model and animations
- slightly adjust piglutton ai

------------------------------------------------------
Version 1.20.6-r1
------------------------------------------------------
- update to 1.20.6
- massive code cleanup
- pigluttons now stalk targets for a while before rushing towards them
- pigluttons now flee for a bit if fed flesh (that isn't yours) or take enough damage, and will despawn if fed enough
- pigluttons are now larger (upscaled for now, will receive a new model and animations later)
- pigluttons now break wooden blocks
- pigluttons now disable shields
- pigluttons can no longer despawn
- pigluttons can no longer spawn on top of you
- pigluttons now drop more experience
- piglutton attributes have been adjusted
- cannibals can now still equip items if it isn't armor
- eating your own flesh now increases piglutton spawn chance
- higher cannibal levels now give jump boost and fall reduction
- cannibal buffs have been rebalanced and are now gradually applied
- high level cannibals are now considered monsters for the purpose of sleeping
- cannibal level now persists on death if tethered
- using a knife on yourself while tethered now untethers you
- tethered heart is now a food item
- dropped flesh now has a pickup delay
- cooking flesh now keeps the name