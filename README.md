# MyProject-BookStore
My Personal Project using Spring Boot, Restful API
Công nghệ sử dụng: Spring Boot, Spring Security JWT, MySql
Các chức năng trong Project:
- Đối với User:
1. Đăng nhập, đăng ký tài khoản
2. Xem thông tin các sản phẩm có trong cửa hàng
3. Quản lý giỏ hàng của mình (Thêm sửa xóa sản phẩm trong giỏ hàng)
4. Đặt hàng
5. Thanh toán bằng VNPay
6. Hủy đặt hàng
7. Xem trạng thái đơn hàng,..
8. Cập nhật thông tin cá nhân
9. Quên mật khẩu
- Đối vơi admin
1. Quản lý sách
2. Quản lý danh mục sản phẩm
3. Quản lý khách hàng
4. Quản lý đơn hàng
5. Gửi thông báo tới User qua email
- Đối với hệ thống
1. Cronjob (Tự động gửi mail chúc mừng sinh nhật vào ngày sinh nhât của User)
2. Phân quyền (User, Admin)
3. Gửi token quên mật khẩu cho user qua email
