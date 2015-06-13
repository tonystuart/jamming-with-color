Jamming lets you compose and perform music using the color, position and size of colored objects.

The current system uses a Raspberry Pi (with integrated camera) that is mounted on an arm and pointed downward toward a table top.

It takes an image of the objects on the table top every second and processes the image to identify the position, shape and color of the objects in the image.

It then converts the color of each object to its relative position in the spectrum (i.e. the colors of the rainbow, from red to violet) and converts that position to a note in a four octave range.

Finally, it uses the relative position and size of each object to convert the notes to a musical sequence.

You can play notes one at a time by placing the objects horizontally (along the x-axis) or compose chords by placing the objects vertically (along the y-axis). The width of each object represents its duration and the height of each object represents its volume (pending).

The resulting sequence is played repeatedly in a loop and the sequence is updated dynamically as you change the objects and their positions.

There are a number of setup parameters including whether it converts the color to notes or drum sounds, the mapping of color to sound (i.e. the four octave range is just one possible mapping), the duration and tempo of the music, the image update frequency, etc.

