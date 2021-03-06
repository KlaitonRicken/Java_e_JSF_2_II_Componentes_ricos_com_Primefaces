package br.com.caelum.livraria.modelo;

import org.primefaces.model.LazyDataModel;
import br.com.caelum.livraria.dao.DAO;

public class LivroDataModel extends LazyDataModel<Livro>{

	private static final long serialVersionUID = 1L;

	DAO<Livro> dao = new DAO<Livro>(Livro.class);
	
	public LivroDataModel() {
	    super.setRowCount(dao.quantidadeDeElementos());
	}
	
//	@Override
//	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
//	    return dao.listaTodosPaginada(inicio, quantidade);
//	}
	
}
