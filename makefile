JFLAGS = -g 
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = AVLNode.java AVLTree.java Comparator.java IntegerComparator.java StringComparator.java IntegerComparatorCounter.java KiPod.java Link.java LinkedList.java ListIterator.java Playlist.java Song.java TestAVLTree.java TestComparators.java TestLinkedList.java TestAVLTreemini.java TestKIIPOD.java TestKiPod.java

default: classes

classes: $(CLASSES:.java=.class)

clean: rm *.class
