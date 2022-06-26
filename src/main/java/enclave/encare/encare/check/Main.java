package enclave.encare.encare.check;

import enclave.encare.encare.model.Category;
import enclave.encare.encare.model.Hospital;
import enclave.encare.encare.repository.CategoryRepository;
import enclave.encare.encare.service.impl.CategoryServiceImpl;
import enclave.encare.encare.service.impl.HospitalServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Category> categories = new ArrayList<Category>();
        categories.add(new Category("Da liễu", "Chuyên khoa da liễu lâm sàng đảm nhận chẩn đoán, điều trị, dự phòng và phục hồi chức năng các bệnh lý thuộc chuyên ngành Phong và Da liễu (da và các phần phụ của da gồm lông, tóc, móng, tuyến mồ hôi). Ngoài ra còn bao gồm các bệnh lây truyền qua đường tình dục, và các bệnh lý liên quan đến da liễu do nhiễm HIV/AIDS. Những căn bệnh thường gặp ở khoa da liễu như: nám da, tàn nhang, mụn/sẹo/rạn da, viêm da cơ địa, viêm da tiếp xúc dị ứng/kích ứng, các bệnh về sắc tố da, lão hoá da, nấm, giang mai, sùi màu gà, vi nấm sâu gây bệnh nội tạng, ung thư tế bào đáy, ung thư tế bào gai, ung thư hắc tố, hôi nách, bớt sắc tố bẩm sinh, u máu, các khối u lành tính ở da, móng chọc thịt, nốt ruồi,..."));
        categories.add(new Category("Răng - Hàm - Mặt", "Khoa Răng - Hàm - Mặt là một trong những phân ngành lớn và quan trọng của khối lâm sàng. Là một chuyên khoa phụ trách điều trị bệnh lý chuyên sâu và thẩm mỹ trên toàn bộ cấu trúc răng (răng, xương răng, tuỷ răng,...), hàm (vòm miệng, quai hàm, khớp hàm,...) và mặt (xương trán, xương gò má, xương thái dương,...).  Khoa Răng - Hàm - Mặt có thể được phân loại thành các chuyên khoa chính, nhằm phục vụ các nhóm nhu cầu khám chữa của người bệnh như: Nha khoa Phục hình, Chấn thương chỉnh hình hàm mặt, Nha khoa tổng quát, Nha khoa Thẩm mỹ và Nội nha."));
        categories.add(new Category("Phục hồi chức năng", "Thuộc khối y học lâm sàng, là một chuyên khoa đặc biệt có chức năng trị liệu bằng các biện pháp y học truyền thống lẫn công nghệ tiên tiến, dưới sự hỗ trợ của các lĩnh vực y học liên quan, để hỗ trợ sức khỏe và phục hồi các chức năng, năng lực vận động và nhận thức tâm lý vốn có, đã mất đi, suy giảm hoặc tiềm ẩn của cơ thể. Khoa Vật lý trị liệu - Phục hồi chức năng được phân chia thành 4 lĩnh vực chuyên môn, gồm: Vận động trị liệu, Vật lý trị liệu, Hoạt động trị liệu và Ngôn ngữ trị liệu."));
        categories.add(new Category("Hoạt động trị liệu", "Chức năng - nhiệm vụ của khoa Hoạt động trị liệu nhằm giúp gia tăng sức khoẻ và năng lực lao động của người bệnh, bằng cách tập luyện chức năng bằng những hoạt động có chọn lọc nhằm gia tăng tầm vận động khớp, sức mạnh cơ, sức bền. Chuyên môn của khoa còn giúp lượng giá năng lực người bệnh về mặt thể chất và tinh thần để cải thiện hướng nghiệp. Các bệnh trạng thường gặp bao gồm: mất năng lực cử động tay chân, mất khả năng thăng bằng khi ngồi, mất khả năng cử động các khớp của chi, mất khả năng hiểu lời nói hay chữ viết, mất sáng kiến, trầm cảm, cảm xúc bất ổn định, mất năng lực diễn tả ý nghĩ hoặc phát ngôn, ý thức về sự mất năng lực thể chất hoặc phẩm giá."));
        categories.add(new Category("Thẩm mỹ", "Khoa thẩm mỹ là một bộ phận của khối y học lâm sàng, dựa trên các chuyên môn nội khoa và ngoại khoa để can thiệp có chủ ý lên cơ thể nhằm mục đích làm đẹp, chủ yếu là diện mạo bên ngoài cùng một số cấu trúc bên trong cơ thể. Khoa Thẩm mỹ có thể phân chia thành: Nội thẩm mỹ và Phẫu thuật thẩm mỹ"));
        categories.add(new Category("Tiêu hóa", "Là một chuyên khoa thuộc khối lâm sàng, giữ chức năng khám chữa tổng hợp các bệnh liên quan đến đường tiêu hóa và các cơ quan phụ trợ tiêu hoá. Chuyên khoa Tiêu hóa - Gan mật bao gồm 2 chuyên khoa nhỏ là Nội Tiêu hóa - Gan mật và Ngoại Tiêu hóa - gan mật"));
        categories.add(new Category("Ngoại Tim mạch", "Phẫu thuật tim mạch là nhiệm vụ chính trong việc khám, điều trị và can thiệp tim mạch, lồng ngực và các mạch máu ngoại vi bằng phương pháp ngoại khoa như nội soi, mổ phanh truyền thống, phẫu thuật can thiệp. Khoa ngoại tim mạch chuyên về điều trị các bệnh lý ngoại khoa có liên quan đến vấn đề lồng ngực - tim mạch như tim bẩm sinh, suy tim, phình động mạch, tổn thương/chấn thương thành ngực, phổi và các cơ quan trong lồng ngực như: gãy xương sườn, xương ức, thủng thành ngực, tổn thương các cơ quan tim, phổi, mạch máu,..."));
        categories.add(new Category("Nội Hô hấp", "Khoa Nội hô hấp với chức năng điều trị tập trung theo phương pháp nội khoa bao gồm khám lâm sàng thực thể phối hợp thăm dò chức năng hô hấp theo phương pháp cận lâm sàng, trị liệu bằng thuốc dạng hít và dạng uống, xông rửa và thông chống nghẹt, chọc dò và sinh thiết. Các bệnh lý thường gặp như: viêm phổi, viêm phế quản, viêm màng phổi không do lao, apxe phổi, ung thư phổi màng phổi, bệnh phổi tắc nghẽn mạn tính,tràn dịch màng phổi không do lao,các bệnh phổi hiếm găp: sarcoidosis, bệnh phổi biệt lập…"));

        CategoryServiceImpl categoryService = new CategoryServiceImpl();

        List<Hospital> hospitals = new ArrayList<Hospital>();
        hospitals.add(new Hospital("Bệnh viên chuyên ngành", 0, 0, 0, 0, "Đà nẵng", "Bênh viện Việt Mỹ"));
        hospitals.add(new Hospital("Bệnh viên da liễu", 0, 0, 0, 0, "Đà nẵng", "Bênh viện da liễu abc"));
        hospitals.add(new Hospital("Bệnh viên tim mạch", 0, 0, 0, 0, "Đà nẵng", "Bênh viện tim mạch xyz"));
        HospitalServiceImpl hospitalService = new HospitalServiceImpl();

        System.out.println("insert finish");
    }
}
