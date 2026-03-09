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



HashCode Caching

Strings are often used as keys in HashMap.

HashCode is cached for performance.

If Strings were mutable, the hashCode would change and break hash-based collections.



Can we make String mutable using reflection?

Answer:

Yes, technically it is possible using reflection, but it is not recommended.

Example:

String s = "Hello";

Field field = String.class.getDeclaredField("value");
field.setAccessible(true);

char[] value = (char[]) field.get(s);
value[0] = 'J';

System.out.println(s);

Output:

Jello

However, modern JVM versions restrict this for security.


9. How does immutability help HashMap?

Answer:

HashMap uses hashCode() to store and retrieve keys.

Since Strings are immutable:

hashCode remains constant

location in HashMap bucket never changes

Example:

HashMap<String, Integer> map = new HashMap<>();
map.put("Java", 1);

If the key changed after insertion, retrieval would fail.

10. Why is String preferred as HashMap key?

Answer:

Because String:

1️⃣ Is immutable
2️⃣ Has cached hashCode
3️⃣ Provides reliable equals() implementation

Example:

Map<String, String> map = new HashMap<>();
map.put("username", "admin");
