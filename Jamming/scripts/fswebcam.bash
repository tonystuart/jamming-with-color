# Available Controls        Current Value   Range
# ------------------        -------------   -----
# Brightness                0 (50%)         -64 - 64
# Contrast                  0 (0%)          0 - 64
# Saturation                64 (50%)        0 - 128
# Hue                       0 (50%)         -40 - 40
# White Balance Temperature, Auto True            True | False
# Gamma                     100 (6%)        72 - 500
# Power Line Frequency      50 Hz           Disabled | 50 Hz | 60 Hz
# Sharpness                 4               0 - 6
# Backlight Compensation    1               0 - 2

fswebcam --device /dev/video0 \
	--set Brightness=0 \
	--set Contrast=0 \
	--set Saturation=64 \
	--set Hue=0 \
	--set "White Balance Temperature, Auto"=false \
	--set Gamma=100 \
	--set "Power Line Frequency"="60 Hz" \
	--set Sharpness=2 \
	--set "Backlight Compensation"=1 \
	--resolution 960x720 \
	--no-banner \
	--loop 10 \
	Jamming.jpg
