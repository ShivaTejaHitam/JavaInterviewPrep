Why are Strings immutable in Java?

Answer:

Strings are immutable for several important reasons:

1️⃣ Security

Strings are widely used in:

File paths

Network connections

Database URLs

Class loading

Immutability prevents malicious changes.

Example:

DatabaseConnection("dbURL")

If Strings were mutable, someone could change the URL after validation.
