# antipattern

**Antipattern** es un proyecto de ejemplo en **Spring Boot** con **Thymeleaf**, diseñado **intencionadamente con malas prácticas de programación**.

El objetivo es utilizarlo como banco de pruebas para analizar qué errores y advertencias detecta una herramienta de análisis estático como **SonarLint** o **SonarQube**, y qué problemas de arquitectura o calidad de código pasan desapercibidos.

---

## Descripción del proyecto

Este proyecto consiste en una aplicación web muy simple que:

- Gestiona usuarios y mensajes en **memoria** (no hay base de datos).
- Utiliza un único **controlador** (`AppController`) que centraliza toda la lógica de negocio, presentación y persistencia.
- Genera páginas HTML mediante **Thymeleaf**.
- No aplica ningún patrón de diseño adecuado como MVC real, separación de capas, ni principios SOLID.

---

## Principales "errores" intencionados

- **Controlador único** con múltiples responsabilidades.
- **Métodos excesivamente largos** y con **alta complejidad ciclomática** (`cleanupInactiveUsers`, `addMessage`, `home`).
- **Captura genérica de excepciones** (`catch (Exception e)` seguido de `e.printStackTrace()`).
- **Duplicación de código** y operaciones repetitivas.
- **Falta de servicios o repositorios separados**.
- **Acoplamiento extremo** entre controlador y modelo de datos.
- **Malas prácticas de concurrencia** (listas mutables sin protección).
- **Condiciones anidadas** que dificultan el mantenimiento.
- **Uso ineficiente de estructuras de control** (`Iterator` y bucles manuales).
- **Variables públicas** en las clases de dominio (`Message`, `User`).

---

## ¿Qué se espera probar?

El propósito de este proyecto es **analizar**:

- Qué problemas **detecta automáticamente SonarLint o SonarQube** (por ejemplo: complejidad de métodos, capturas genéricas de excepciones, variables públicas, duplicaciones, etc.).
- Qué problemas **no son detectados** fácilmente (por ejemplo: violaciones de arquitectura, falta de separación de responsabilidades, falta de inyección de dependencias).
- Cómo ajustar reglas y perfiles de calidad para mejorar la detección de errores reales en proyectos reales.

---


## Nota
Este proyecto no debe usarse como referencia de buenas prácticas de desarrollo en Spring Boot.
Está diseñado deliberadamente como un antipatron (anti-pattern) para fines de aprendizaje y experimentación.
