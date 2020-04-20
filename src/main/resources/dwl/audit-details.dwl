%dw 2.0
output application/java
---
payload.lines filter ($.'primary-key' != null and $.'primary-key' != '') map $
