# Orçamento Fácil

Projeto Android em **Kotlin + Jetpack Compose + Room + MVVM** para controle de gastos por períodos personalizados.

## O que o app faz

- cadastra períodos base, por exemplo `15 -> 09`
- cadastra tipos de gastos por período, por exemplo `Cartão crédito Santander`, `Débito`, `Cheque especial`
- lança gastos por valor, data e tipo
- identifica automaticamente o período correto pelo **tipo de gasto** e pela **data informada**
- armazena tudo em banco local **SQLite** via **Room**
- mostra histórico dos períodos e lançamentos
- permite editar o modelo do período e os tipos de gastos
- renova automaticamente os períodos para o mês seguinte

## Estrutura

- `data/local`: entidades Room, DAOs e banco
- `domain`: regras de negócio e repositório
- `ui/viewmodel`: ViewModels e estados de tela
- `ui/screens`: telas Compose
- `ui/navigation`: navegação do app
- `util`: utilitários de data

## Regras principais

1. Cada modelo define:
   - nome
   - dia inicial
   - dia final
   - limite total
   - lista de tipos de gasto
2. Cada tipo pertence a um único modelo.
3. Ao lançar um gasto:
   - o usuário escolhe o tipo
   - informa valor e data
   - o app encontra o período daquele modelo que contém a data
   - se necessário, cria automaticamente o período daquele mês
4. O histórico fica salvo em `Room/SQLite`.

## Requisitos

- Android Studio Hedgehog+ ou mais novo
- JDK 17
- Android SDK 35

## Observação importante

O projeto foi gerado completo em código, mas eu não consegui executar um build real aqui dentro porque este ambiente não tem o Android SDK/Gradle Wrapper configurado para compilar APK. A estrutura, dependências e classes principais já estão montadas para abrir no Android Studio e sincronizar.

## Próximos passos sugeridos

1. abrir no Android Studio
2. deixar o Gradle baixar as dependências
3. gerar o `gradle wrapper` se quiser versionar completo
4. executar em emulador/dispositivo




https://github.com/user-attachments/assets/6e107d7f-71f4-4081-bdbd-cd4619fa0079


