NAME = 190_Final
GSPEC = $(NAME).gspec
GSPEC_LPKF = $(NAME)_LPKF.gspec
BRD = $(NAME).preroute.brd
LPKF_PRE_BRD = $(NAME).preroute_lpkf.brd
LPKF_BRD = $(NAME).postroute.brd
OUT_DIR = ./gerber
LPKF_DIR = ./lpkf
ZIP = $(OUT_DIR)/$(NAME)_gerber.zip
LPKF_ZIP = $(LPKF_DIR)/$(NAME)_gerber.zip
cam:
	runEagleCam.py --gspec $(GSPEC) --brd $(BRD) --dir $(OUT_DIR) --zip $(ZIP) --eagle $(EAGLE_EXE)
.PHONY: lpkf
lpkf:
	boardmill.py -i $(LPKF_PRE_BRD) -o $(LPKF_BRD) -g $(GSPEC_LPKF) --postroute -p 1
	runEagleCam.py --gspec $(GSPEC_LPKF) --brd $(LPKF_BRD) --dir $(LPKF_DIR) --zip $(LPKF_ZIP) --eagle $(EAGLE_EXE)
%.gerbers : %.brd
	mkdir $*_gerbers
	#boardmill.py -i $< -o $*.post_route -g $(GSPEC_LPKF) --postroute -p 1
	runEagleCam.py --gspec $(GSPEC_LPKF) --brd $< --dir $(LPKF_DIR)/$* --zip $*.zip --eagle $(EAGLE_EXE)
clean:
	make -C $(OUT_DIR) clean
	make -C $(LPKF_DIR) clean
	rm -f *.pro
	rm -f *.job
	rm -f *.b#*
