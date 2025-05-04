# PC Crawler

## Ejecución con argumentos
```
java PCCrawler [-menu] [-cargar] [-iter] [-recur] [-file nombre_archivo] [-all][-search <tokens_a_buscar>][-multi <tokens_a_buscar>]
```
* `-menu`: Muestra un menú interactivo para realizar operaciones sobre el diccionario antes de terminar la ejecución.

* `-cargar`: Carga el diccionario almacenado en el fichero «diccionario.ser».

* `-iter`: Selecciona el diccionario de enfoque iterativo y lo almacena en el fichero «diccionario.ser». Es necesario pasarle el nombre del fichero a analizar (* `-file nombre_archivo`).

* `-recur`: Selecciona el diccionario de enfoque recursivo y lo almacena en el fichero «diccionario.ser». Es necesario pasarle el nombre del fichero a analizar (* `-file nombre_archivo`).
* 
* `-sinonimia`: Activa la búsqueda de sinónimos.

* `-file nombre_archivo`: Indica el nombre del fichero a analizar.

* `-all`: Muestra el diccionario completo.

* `-search tokens_a_buscar`: Busca los términos indicados en el diccionario. Este debe ser el último argumento, seguido de los términos a buscar.
* 
* `-multi tokens_a_buscar`: Busca en el diccionario los documentos que contengan simultáneamente los términos a buscar. Este debe ser el último argumento, seguido de los términos a buscar.

* `-help`: Muestra la ayuda. También es el caso por defecto si no se le pasa ningún argumento o si no se reconoce el argumento pasado.

## Menú principal (modo interactivo)

0. **Terminar consulta**. Termina el programa.
1. **Consultar diccionario**. Consulta los términos y las ocurrencias de TODO el diccionario.
2. **Buscar un token (o BIGRAMA) en el diccionario**. Busca un token, que puede estar formado por una o dos palabras (bigrama), en el diccionario.
3. **Consultar Thesauro**. Consulta todas las entradas del Thesauro.
4. **Buscar un token en el Thesauro**. Busca un término en el Thesauro.
5. **Mostrar índice de documentos**. Muestra todos los documentos analizados con sus índices.
6. **Buscar un token y sus SINÓNIMOS**. Busca un token y sus sinónimos en el diccionario. No funciona con bigramas.
7. **Mostrar bigramas**. Muestra todos los bigramas del diccionario.
8. **Buscar multitérminos**. Busca varios términos en el diccionario a la vez, y devuelve únicamente aquellos documentos en los que todos estos términos aparezcan simultáneamente.
9. **Buscar multitérminos con SINONIMIA**. Busca varios términos (y sus sinónimos) en el diccionario a la vez, y devuelve únicamente aquellos documentos en los que todos estos términos (o sus sinónimos) aparezcan simultáneamente.

