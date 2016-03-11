# JDBC
1.First please put the xml file and the dtd file into the bin folder.
2.Change the file name in Test.java to the target xml file.
3.The connection to the database is defined in JDBC class, so you may need to change the information before.
4.Run this code, the main function is in Test.java.

Some explanations
1.This program used SAX Parser to parse the xml file and send one sql sentence to database when the information of one object is ready.
2.The ampersands in booktitle and authornames have been specially dealt with.
