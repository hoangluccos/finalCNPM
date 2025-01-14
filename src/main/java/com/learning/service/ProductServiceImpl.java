package com.learning.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.learning.model.Image;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.learning.model.Product;
import com.learning.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public Product save(Product entity) {
		return productRepository.save(entity);
	}


	@Override
	public List<Product> findProductsByType(Integer id) {
		return productRepository.findByType(id);
	}
	@Override
	public List<Product> saveAll(List<Product> entities) {
		return (List<Product>)productRepository.saveAll(entities);
	}

	@Override
	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return productRepository.existsById(id);
	}

	@Override
	public List<Product> findAll() {
		return (List<Product>)productRepository.findAll();
	}

	@Override
	public List<Product> findAllById(List<Integer> ids) {
		return (List<Product>)productRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public void deleteAllById(List<Integer> ids) {
		productRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAll(List<Product> entities) {
		productRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

	@Override
	public Page<Product> listAll(int pageNum, String sortField, String sortDir, String keyword) {
		int pageSize = 8;
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
				sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());


		if(keyword != null )
		{
			return productRepository.findAll(keyword, pageable); // the findAll we already custom
		}
		return productRepository.findAll(pageable);
	}

	@Override
	public Page<Product> listAllByCategory(int pageNum, String sortField, String sortDir, String keyword) {
		int pageSize = 8;
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
				sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());


		if(keyword != null )
		{
			return productRepository.findAllByCategory(keyword, pageable); // the findAll we already custom
		}
		return productRepository.findAll(pageable);
	}
	@Override
	public List<String> getImageSrcsByProductId(int productId) {
		Product product = findById((int) productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
		return product.getImages().stream()
				.map(Image::getImage_src)
				.collect(Collectors.toList());
	}

}
