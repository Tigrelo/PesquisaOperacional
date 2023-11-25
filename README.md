# POO 


## Contextualização do Problema
O problema de transporte é uma situação comum em logística, onde o objetivo é minimizar o custo de distribuição de produtos de vários fornecedores para vários consumidores, respeitando as capacidades de oferta e demanda.

## Modelagem Matemática
O problema pode ser modelado usando programação linear. As variáveis de decisão, as restrições e a função objetivo devem ser definidas para resolver o problema.

### Exemplo de Modelagem

- Variáveis de Decisão: Xij representa a quantidade a ser transportada do fornecedor i para o consumidor j.
- Restrições: Garantir que a oferta dos fornecedores seja atendida e a demanda dos consumidores seja satisfeita.
- Função Objetivo: Minimizar o custo total de transporte.

## Exemplo de Solução
O código implementa um algoritmo para resolver o problema de transporte com 50 fornecedores e 50 consumidores. Ele gera custos aleatórios, oferta e demanda, e utiliza o SimplexSolver para encontrar a solução ótima.

### Interpretação dos Resultados
A solução obtida mostra a quantidade ótima a ser transportada de cada fornecedor para cada consumidor, minimizando o custo total de transporte. É importante analisar como essa alocação afeta o custo e se atende às restrições de oferta e demanda.

---
