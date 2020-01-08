.PHONY: clean doc test

TARGET = bin
SOURCE = src
PACKAGE = com
TEST = test
DOC = doc
JAR = convexHull
DATA = Varoumas_benchmark
SOURCE_FILES = $(wildcard $(SOURCE)/**/**/*.java $(SOURCE)/**/*.java)
SOURCE_TEST = $(wildcard $(TEST)/**/*.java)


file: $(patsubst $(SOURCE)/%.java,$(TARGET)/%.class,$(SOURCE_FILES))

$(TARGET)/%.class: $(SOURCE)/%.java
	javac -sourcepath $(SOURCE) $^ -d $(TARGET)

run: file
	cd $(TARGET); java $(PACKAGE).Main

clean:
	rm -frv $(TARGET)