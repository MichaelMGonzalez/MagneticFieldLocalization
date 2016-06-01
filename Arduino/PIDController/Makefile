CC = g++
CXXFLAGS = -std=c++11
DATA = pid_sim.csv
PROG_NAME = tester
EXC = ./$(PROG_NAME)

$(DATA): $(PROG_NAME) data
data:
	$(EXC) >> $(DATA)
plot: $(DATA)
	python Plotter.py $(DATA)
$(PROG_NAME): PIDController.o util.o
run: $(PROG_NAME) 
	$(EXC)
.PHONY: clean
clean: 
	rm -f tester *.o $(DATA)
