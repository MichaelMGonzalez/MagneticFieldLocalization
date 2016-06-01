CC = g++
CXXFLAGS = -std=c++11
PROG_NAME = tester
EXC = ./$(PROG_NAME)

$(PROG_NAME): DifferentialDrive.o 
run: $(PROG_NAME) 
	$(EXC)
.PHONY: clean
clean: 
	rm -f tester *.o $(DATA)
