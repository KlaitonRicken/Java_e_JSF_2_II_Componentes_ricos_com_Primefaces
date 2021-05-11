package br.com.caelum.livraria.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;
import br.com.caelum.livraria.util.ForwardView;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;
	private List<Livro> livros;
	private LivroDataModel ldm = new LivroDataModel();
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");

	public List<String> getGeneros() {
		return generos;
	}

	public void setGeneros(List<String> generos) {
		this.generos = generos;
	}

	public LivroDataModel getLdm() {
		return ldm;
	}

	public void setLdm(LivroDataModel ldm) {
		this.ldm = ldm;
	}

	public Livro getLivro() {
		return livro;
	}
	
	public Integer getLivroId() {
        return livroId;
    }
	
	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregaPelaId() {
		this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livroId);
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
//			throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um autor!"));
			return;
		}

		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if(this.livro.getId() == null){
			dao.adiciona(this.livro);
			this.livros = dao.listaTodos();
		}else{
			System.out.println("Gravando alteração");
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		}
		
		this.livro = new Livro();
	}
	
	public void removerAutorDoLivro(Autor autor){
		this.livro.removeAutor(autor);
	}
	
	public List<Autor> getAutores(){
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void gravarAutor(){
		Autor autor  = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
//		this.livro.clearAutores();
		System.out.println("gravando autor");
	}
	
	public void remover(Livro livro){
		System.out.println("Removendo livro");
		
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		dao.remove(livro);
		this.livros = dao.listaTodos();
	}
	
	public void update(Livro livro){
		System.out.println("Alterando livro");
		this.livro = livro;
	}
	
	public ForwardView formAutor(){
		return new ForwardView(new RedirectView("autor").toString());
	}
	
	public ForwardView formCarosel(){
		return new ForwardView(new RedirectView("carosel").toString());
	}
	
	public ForwardView formLivro(){
		return new ForwardView(new RedirectView("livro").toString());
	}
	
	public ForwardView formRing(){
		return new ForwardView(new RedirectView("ring").toString());
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public List<Livro> getLivros(){
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if(this.livros == null)
			this.livros = dao.listaTodos();
		return livros;
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException{
		String valor = value.toString();
		if(!valor.startsWith("1")){
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}
	
	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { // java.util.Locale

        //tirando espaços do filtro
        String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

        System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

        // o filtro é nulo ou vazio?
        if (textoDigitado == null || textoDigitado.equals("")) {
            return true;
        }

        // elemento da tabela é nulo?
        if (valorColuna == null) {
            return false;
        }

        try {
            // fazendo o parsing do filtro para converter para Double
            Double precoDigitado = Double.valueOf(textoDigitado);
            Double precoColuna = (Double) valorColuna;

            // comparando os valores, compareTo devolve um valor negativo se o value é menor do que o filtro
            return precoColuna.compareTo(precoDigitado) < 0;

        } catch (NumberFormatException e) {

            // usuario nao digitou um numero
            return false;
        }
}
}
