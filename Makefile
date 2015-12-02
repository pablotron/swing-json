
all: 
	javac -cp .:src:jars/* $(shell find . -type f -name \*.java)

test: all
	java -cp .:src:jars/* test.SwingJsonTest

clean:
	find . -type f -name \*.class -print0 | xargs -0 rm -v
