package br.com.caelum.livraria.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@ManagedBean
@ViewScoped
public class VendaBean {

	Venda venda;
	
	
	public List<Venda> getVendas(long seed){
		
		List<Livro> livros  = new DAO<Livro>(Livro.class).listaTodos();
		List<Venda> vendas  = new ArrayList<Venda>();
		
		Random rand = new Random(seed);
		
		for(Livro livro : livros){
			Integer quantidade = rand.nextInt(500);
			vendas.add(new Venda(livro, quantidade));
		}
		
		return vendas;
	}
	
	public BarChartModel getVendasModel(){
		
		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		
		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2016");
		
		List<Venda> vendas = getVendas(1234);
		for(Venda venda : vendas){
			vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		
		ChartSeries vendaSerie2021 = new ChartSeries();
		vendaSerie2021.setLabel("Vendas 2021");
		
		vendas = getVendas(4321);
		for(Venda venda : vendas){
			vendaSerie2021.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		
		model.addSeries(vendaSerie);
		model.addSeries(vendaSerie2021);
		
		return model;
		
	}
	
}
