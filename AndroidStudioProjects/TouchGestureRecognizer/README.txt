Jason Bingham
CS 3200
Assignment 7
Gesture Recognition

I never got around to working on the Euclidian Distance matching, so I can't talk about my experiences with it or compare it with zone vector matching.

I found that the zone vector matching works the majority of the time. The way I have it set up is that the size of the zones is determined by the size of the gesture. I did this because I was worried that if the gestures weren't the same size or on the same area of the screen, the matching would not work. I can't tell if it is better than the Euclidian matching since I did not get that one done. I found that the zone matching would fail more the lazier the gestures got. For example, I tested the number 5 and if it turned more into an S shape than 5, the matching would determine it was a 3. There were also a couple times where it thought that a 4 was a 2. I would have to say that there are better methods than this type of matching.
