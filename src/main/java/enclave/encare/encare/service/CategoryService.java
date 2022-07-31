package enclave.encare.encare.service;

import enclave.encare.encare.model.Category;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.modelResponse.CategoryResponse;
import enclave.encare.encare.repository.CategoryRepository;

import java.util.List;

public interface CategoryService {
    CategoryResponse findById(long id);
    List<CategoryResponse> listCategory();
    Category listDoctorOfCategory(long category);
    void saveAll(List<Category> categoryList);
    void delete();
}
