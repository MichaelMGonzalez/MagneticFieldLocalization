NAME = 190_Final
GSPEC = $(NAME).gspec
BRD = $(NAME).preroute.brd
OUT_DIR = ./gerber
ZIP = $(OUT_DIR)/$(NAME)_gerber.zip
cam:
	runEagleCam.py --gspec $(GSPEC) --brd $(BRD) --dir $(OUT_DIR) --zip $(ZIP) --eagle $(EAGLE_EXE)
clean:
	make -C $(OUT_DIR) clean
	rm -f *.pro
	rm -f *.job
