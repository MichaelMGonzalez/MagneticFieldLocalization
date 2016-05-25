CC = g++
CXXFLAGS = -std=c++11
DATA = pid_sim.csv
EXC = ./tester

run: tester
	$(EXC)
data:
	$(EXC) > $(DATA)
plot: data
	python Plotter.py $(DATA)
tester: PIDController.o
clean: 
	rm -f tester *.o $(DATA)
