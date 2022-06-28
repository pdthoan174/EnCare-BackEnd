package enclave.encare.encare.service.impl;

import enclave.encare.encare.model.Category;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.modelResponse.CategoryResponse;
import enclave.encare.encare.repository.CategoryRepository;
import enclave.encare.encare.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryResponse findById(long id) {
        return transformData(categoryRepository.findByCategoryId(id));
    }

    @Override
    public List<CategoryResponse> listCategory() {
        List<Category> list = categoryRepository.findAll();
        List<CategoryResponse> categoryResponseList = new ArrayList<CategoryResponse>();
        for (Category category:list){
            CategoryResponse categoryResponse = transformData(category);
            categoryResponseList.add(categoryResponse);
        }
        return categoryResponseList;
    }

    @Override
    public Category listDoctorOfCategory(long categoryId) {

        return null;
    }

    private CategoryResponse transformData(Category category){
        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setCategoryId(category.getCategoryId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());

        return categoryResponse;
    }
}
