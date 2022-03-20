package br.com.cursospring.primeiroexemplo.repository;

import br.com.cursospring.primeiroexemplo.model.Produto;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

/**
* @Repository: com essa anotação ele faz o import do spring,  quando colocamos essa anotação ,estamos dizendo para
* o spring que isso é um repositório , e o spring vai ter controle sobre o repositório, e com isso podemos usar
* injeção de dependencia e inversão de controle,quando colocamos a anotattion @Repository estamos dando a possibilidade
* do spring ter essa dependência, ou seja estamos injetando essa dependencia dentro do Spring, e quando precisar do
* Repository pedimos o controle para o Spring, e ele devolve uma instancia pronta
* @return
* */

/**
 * O que são Beans no Spring?
 * No Spring, os objetos que formam a espinha dorsal da sua aplicação e que sejam gerenciados pelo Spring são chamados de beans.
 * Um bean é um objeto que é instanciado, montado e gerenciado pelo Spring IoC container.*/
@Repository
public class ProdutoRepository {

    private List<Produto>produtos = new ArrayList<Produto>();

    private Integer ultimoId=0; //simula o id incremental do Banco de dados

   /**
    * Método para retornar  uma lista de produtos
    * @return Lista de produto
    *  */

    public List<Produto> obterTodos(){
        return  produtos;

    }

    //findFirst() devolve um optinional de produto
    //optional foi uma classe criada para facilitar e não dá erro
    //ou seja quando usamos o optional ele devolve o que encontrou
    //ou um vazio(null). Se ele não encontrar nenhum produto aqui dentro
    //ele devolve algo nulo que não dê erro, não estoura uma exceção
    /**
     *Metodo que retorna o produto encontrada pelo o seu Id
     * @param id  do produto que será localizado
     * @return  retorna um produto caso tenha encontrado
     *
     */

    public Optional <Produto> obterPorId(Integer id){
        return produtos
                .stream()
                .filter(produto-> produto.getId() == id)
                .findFirst();
    }
/**
 * Metodo para adicionar produto na lista
 * @param produto que sera adicionado
 * @return  retorna o produto que foi adicionado na lista
 */
    public Produto adicionar(Produto produto){
        ultimoId++;//poderia usar UltimoId+=1; ultimoId++ estou dizendo que esse atributo será incrementado de 1 em 1

        produto.setId(ultimoId);
        produtos.add(produto);

        return produto;
    }
/**
 * Metodo para deletar o produto por id.
 * @param id do produto a ser deletado
 */
    public void deletar(Integer id){
        produtos.removeIf(produto -> produto.getId()==id);
    }

    /**
     * Metodo para atualizar o produto na lista
     * @param produto que será atualizado
     * @return retorna o produto após atualizar a lista
     */


    public Produto atualizar(Produto produto){
        //encontrar produto na lista

        Optional <Produto> produtoEncontrado = obterPorId(produto.getId());
        if(produtoEncontrado.isEmpty()){//se produto vazio
            throw  new InputMismatchException("PRODUTO NÃO ENCONTRADO");
        }

        //eu tenho que primeiro remover o produto antigo da lista
        deletar(produto.getId());//deleta o produto antigo da lista

        //depois adicionar o produto atualizado na lista
        produtos.add(produto);

        //depois devolver o produto depois de adicionar na lista
        return produto;//quer dizer que eu estou devolvendo o mesmo produto ao usuario

    }


}
