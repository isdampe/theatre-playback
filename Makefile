CXX = gcc
src = $(wildcard src/*.c)
obj = $(src:.c=.o)

LDFLAGS = -O3
OSFLAGS = -std=c11

sc:
	sc -i "./src/*.sc"
	$(vht)

tp: $(obj)
	@mkdir -p bin
	$(CXX) -o bin/$@ $^ $(LDFLAGS) $(OSFLAGS)

clean:
	rm src/*.c
	rm src/*.h
	@rm $(obj)
