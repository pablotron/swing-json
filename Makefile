
all: 
	javac -cp src:src/* $(shell find src -type f -name \*.java)

test: all
	java -cp src:src/* org.pablotron.swingjson.test.SwingJsonTest

clean:
	find src -type f -name \*.class -print0 | xargs -0 rm -v
