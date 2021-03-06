# Pokédex App
App desenvolvido que apresenta a Pokédex consumindo o arquivo [JSON](https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json) que mostra um total de 150 Pokémons.
<br>O app utiliza a biblioteca [Retrofit](https://square.github.io/retrofit/) para fazer requisições para a API e conceitos de Reactive Programming utilizando RxJava2. 
<br>Para fazer a exibição das imagens utiliza-se a biblioteca [Glide](https://github.com/bumptech/glide).
<br>No app é possível fazer a busca entre os 150 Pokémons através da biblioteca [Material Search Bar](https://github.com/mancj/MaterialSearchBar), que também apresenta sugestões de busca. 
<br>Ao fazer o click em cima do Pokémon escolhido o usuário é levado para outro Fragment onde são mostrados os detalhes do Pokémon, contendo Tipo, Fraquesas e Evoluções, através de cards utilizando a biblioteca [ChipView](https://github.com/robertlevonyan/material-chip-view).

<br>![exemplo](https://media2.giphy.com/media/q3YgDXAzFRvKQSPLeU/giphy.gif?cid=790b7611de500cc1b6d4ab6ef941ad041483297a26747f95&rid=giphy.gif&ct=gf)
