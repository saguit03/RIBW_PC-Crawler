# **PC Crawler**

## Introducción al PC Crawler

Un *Web Crawler* (rastreador, indexador) es un programa que navega a través de Internet siguiendo enlaces de diferentes páginas y recopilando información que pueda resultar útil para motores de búsqueda, análisis de datos o *scrapping*.

En la práctica se programa un *PC Crawler* que navega por los archivos de un directorio dentro del ordenador y analiza todos los términos de los documentos de texto que aparecen. Como realiza el análisis dentro del ordenador y no en la web, se le denomina *PC Crawler*.

## Diseño del PC Crawler

El *PC Crawler* se encarga de recopilar todos los *tokens* (términos) que aparecen en los documentos de texto de un directorio y almacenarlos en un diccionario, junto con la frecuencia de cada *token* y la ruta absoluta del documento donde se ha encontrado cada uno.

Se distinguen cuatro partes esenciales en el diseño del *PC Crawler*:

- Diccionario. Procesa ficheros para recoger todos los *tokens* y sus ocurrencias. Se han definido dos tipos de diccionario, explicados más adelante.
- Ocurrencia. Guarda la frecuencia total del término (número total de veces que aparece en todos los documentos), la ruta absoluta de los documentos en los que se ha encontrado y la frecuencia local de cada documento (número de veces que aparece en un documento).
- Almacenamiento de objetos. Guarda y carga el diccionario en un fichero «diccionario.ser».
- Programa principal. Se encarga de la ejecución del programa, permitiendo tanto el uso de argumentos como un menú interactivo, para realizar operaciones sobre el diccionario.

### Diccionario de términos

Cada término o *token* encontrado en un documento se almacena en un diccionario que recoge el término y todas las ocurrencias (estructura creada) extraídas a lo largo de la ejecución del programa.

Una ocurrencia de un término se encarga de guardar la frecuencia del término y la ruta absoluta del fichero en la que se ha encontrado dicho término. Esta estructura actualiza su contenido cada vez que encuentra de nuevo el mismo término.


### Consulta al diccionario

Para consultar los términos que el diccionario haya almacenado, hay varias opciones:

- Mostrar el diccionario completo.
- Buscar un término concreto (a través de un menú interactivo).
- Buscar varios términos (a través de argumentos).

Cuando se consulta el diccionario completo, se devuelven todos los términos que haya almacenados, junto a sus ocurrencias (frecuencia total, rutas absolutas y frecuencias locales).

Sin embargo, cuando se buscan términos concretos, solamente se devuelve la frecuencia total del término si se encuentra en el diccionario. En caso contrario, imprime un mensaje de error.

Al final de la entrada, se explicará cómo ejecutar el programa.

## Desarrollo del PC Crawler

Se proporcionó el código base para listar los ficheros de un directorio, tokenizar un documento, y cargar y almacenar objetos en un fichero. A partir de este código base, en una primera sesión se desarrolló el *PC Crawler* de forma recursiva, y en una segunda sesión se amplió el desarrollo para que se pudiera ejecutar de forma iterativa.

### Enfoque recursivo del PC Crawler

Al inicio del desarrollo se tomó la decisión de diseñar el *PC Crawler* de forma recursiva debido a que resultaba más intuitivo de entender y era sencillo seguir su ejecución. Empleando un diseño recursivo, el programa ejecutaba la lectura del primer fichero y se encontraba dos posibilidades:

- El fichero es un directorio. Consulta su contenido y realiza una llamada recursiva al mismo método, pasándole como argumento el directorio encontrado, sin terminar de analizar el directorio actual.
- El fichero es un documento (fichero de texto). En este caso, se tokeniza el documento, recopilando y almacenando todos los *tokens* y sus ocurrencias en el diccionario.

Aunque resulta una solución intuitiva y fácil de programar, puede llegar a alcanzar un coste de computación grande si existiesen muchos elementos dentro del directorio a analizar. Siguiendo la recomendación del profesor, se decidió implementar también el diseño del *PC Crawler* iterativo. 

### Enfoque iterativo del PC Crawler

A diferencia del recursivo, el diseño iterativo requiere de una cola que almacena el primer fichero a analizar. El programa extrae el primer fichero de la cola y realiza la lectura del mismo, encontrando dos posibilidades:

- El fichero es un directorio. Consulta su contenido y encola todos los ficheros (tanto directorios como documentos) que encuentre.
- El fichero es un documento (fichero de texto). En este caso, se tokeniza el documento, recopilando y almacenando todos los *tokens* y sus ocurrencias en el diccionario.

De esta forma, se recorren de forma secuencial todos los ficheros, realizando diferentes acciones (encolar o analizar) dependiendo de la naturaleza del fichero.

En un primer momento, se implementó el diseño iterativo del *PC Crawler* con una cola de ficheros a analizar. Posteriormente, se decidió cambiar la cola de ficheros por una cola de cadenas (Queue<String>), ya que se revisó su eficiencia junto al profesor: sería más eficiente (no crean objetos si no se van a utilizar) y, en futuras prácticas, sería más sencillo de reutilizar.

### Abstracción del diccionario

Dado que ambos enfoques del *PC Crawler* comparten muchas características en común y solo difieren en el comportamiento del análisis de ficheros (leerFichero), se consideró que sería interesante abstraer la clase Diccionario como una interfaz, que se implementaría en una clase base (DiccionarioBase) con los métodos comunes. Luego se crearían dos clases que heredan de DiccionarioBase, una para el enfoque recursivo (DiccionarioRecursivo) y otra para el enfoque iterativo (DiccionarioIterativo).

Además, se creó un tipo enumerado (TipoDiccionario) para poder diferenciar los tipos de diccionario durante la ejecución del programa:

- **NINGUNO**. Cuando no se ha elegido un tipo de diccionario.
- **ITERATIVO**. Para el diccionario de enfoque iterativo.
- **RECURSIVO**. Para el diccionario de enfoque recursivo.
- **CUALQUIERA**. Si no importa el tipo de diccionario elegido, por ejemplo, al cargar el diccionario de un fichero.

## Ejecución del PC Crawler

Se ofrecen dos opciones: ejecutar el programa con argumentos (como si fuera un comando UNIX) o ejecutarlo de forma interactiva (**-menu**).

### Ejecución con argumentos

Para ejecutar el programa con argumentos, se debe seguir la siguiente estructura:

```bash
java PCCrawler [-menu] [-cargar] [-iter] [-recur] [-file nombre_archivo] [-all] [-search tokens_a_buscar]
```

- **-menu**: Muestra un menú interactivo para realizar operaciones sobre el diccionario antes de terminar la ejecución.
- **-cargar**: Carga el diccionario almacenado en el fichero «diccionario.ser».
- **-iter**: Selecciona el diccionario de enfoque iterativo y lo almacena en el fichero «diccionario.ser». Es necesario pasarle el nombre del fichero a analizar (**-file nombre_archivo**).
- **-recur**: Selecciona el diccionario de enfoque recursivo y lo almacena en el fichero «diccionario.ser». Es necesario pasarle el nombre del fichero a analizar (**-file nombre_archivo**).
- **-file nombre_archivo**: Indica el nombre del fichero a analizar.
- **-all**: Muestra el diccionario completo.
- **-search tokens_a_buscar**: Busca los términos indicados en el diccionario. Este debe ser el último argumento, seguido de los términos a buscar.
- **-help**: Muestra la ayuda. También es el caso por defecto si no se le pasa ningún argumento o si no se reconoce el argumento pasado.

Si no se le pasa alguno de los argumentos [-cargar] [-iter] [-recur], se mostrará un menú interactivo para elegir el tipo de diccionario. Sin embargo, es necesario pasarle el nombre del fichero a analizar (**-file nombre_archivo**) si no se obtiene el diccionario de un fichero.

### Ejecución interactiva

Para ejecutar el programa de forma interactiva, se debe seguir pasar el argumento **-menu**. Se mostrará el menú interactivo, donde se podrá realizar las siguientes operaciones:

- 0. Consultar diccionario.
- 1. Buscar un token en el diccionario.
- 2. Terminar consulta.

Si se elige buscar un token en el diccionario, se podrán introducir los *tokens* a buscar. No se parará la ejecución hasta que se introduzca el número 0 o se presione Ctrl+C.

```txt
------------------
CONSULTA DE TOKENS
------------------
1. Consultar diccionario
2. Buscar un token en el diccionario
3. Terminar consulta
Opción seleccionada: 1
Token a consultar: gatos
El token «gatos» se encuentra 2 veces.
Token a consultar: dir
El token «dir» se encuentra 1 veces.
Token a consultar: 0

Process finished with exit code 0
```

Si se desea elegir de forma interactiva el tipo de diccionario, se deben omitir los argumentos [-cargar] [-iter] [-recur], ya que si no se detecta ninguno de estos argumentos, se mostrará el menú interactivo para elegir el tipo. Sin embargo, es necesario pasarle el nombre del fichero a analizar (**-file nombre_archivo**) si no se obtiene el diccionario de un fichero.

En futuras prácticas, se ampliarán los tipos de diccionario y se añadirán nuevas funcionalidades al programa. ¡Quedaos atentos a las próximas entradas!