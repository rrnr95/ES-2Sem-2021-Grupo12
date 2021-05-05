### Grupo 12:
Nome|Número|User
----|------|----
António Rodrigo|87969|AntonioRodrigo92
Diogo Francisco|87596|DiogoFrnc
Erica Évora|87571|erikaevora
Renato Rosa|82836|rrnr95
Rui Gomes|88068|ruifogomes

- [Trello](https://trello.com/dozeteam/home)
- [RequesitosProjectoExcell](https://iscteiul365-my.sharepoint.com/:x:/g/personal/djfos_iscte-iul_pt/ESxIaj1pGdpFnroG__hC9-8BSt-nE5ro8rR9yStfFjqYfQ?e=fvxZNN)

> Para efeitos de entrega do projeto, deve ser associada a tag **_CodeQualityAssessor-1.0_** ao último commit feito no
branch master

<br>
<hr>
<strong>Utilização da Aplicação</strong>
<br>
O objetivo deste software é o levantamento de code smells de um dado projeto. Como tal, ao iniciar a aplicação, o utilizador deverá indicar a raiz do projeto que deseja analisar, através do botão <strong><i>Folder</i></strong>. O botão <strong><i>Calculate Metrics</i></strong> fará o levantamento das métricas do projeto, fornecendo um resumo geral do mesmo e criando um ficheiro XLSX na raiz. O botão <strong><i>Fetch XLSX</i></strong> apresenta todas as métricas do projeto, através da leitura do ficheiro XLSX previamente criado. 
De forma a definir as métricas usadas para os Code Smells, é possível gerir as regras através do botão <strong><i>Rules</i></strong>. A regra apenas ficará criada quando lhe for atribuida um nome não-existente e após pressionar o botão <strong><i>Add Rule</i></strong>
<ul>
  <li>NOM_class: numero de metodos da classe</li>
  <li>LOC_class: linhas de código da classe</li>
  <li>WMC_class: complexidade ciclomatica da classe</li>
  <li>LOC_method: numero de linhas de codigo do metodo</li>
  <li>CYCLO_method: complexidade ciclomatica do metodo</li>
</ul>
<br>
o botão <strong><i>Classification Quality</i></strong> irá apresentar uma matriz de confusão, comparando as metricas recolhidas com as regras do utilizador e um ficheiro XLSX. Apresentará também as predições corretas e incorretas.

<br>
<hr>
<strong>Para utilizar este software como Docker Image</strong>:<br>
  1) instalar VcXsrv (https://sourceforge.net/projects/vcxsrv/)<br>
  2) lançar o XLaunch<br>
  3) download da imagem que contém o projeto (docker pull antoniorodrigo92/esgrupo12codesmells)<br>
  4) correr a imagem - CMD: docker run -v C:\Users:/C antoniorodrigo92/esgrupo12codesmells<br>
