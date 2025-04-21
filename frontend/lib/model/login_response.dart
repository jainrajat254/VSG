class LoginResponse {
  final String id;
  final String token;
  final String name;
  final String email;
  final String username;
  final String phone;

  LoginResponse({
    required this.id,
    required this.token,
    required this.name,
    required this.email,
    required this.username,
    required this.phone,
  });

  factory LoginResponse.fromJson(Map<String, dynamic> json) {
    return LoginResponse(
      id: json['id'],
      token: json['token'],
      name: json['name'],
      email: json['email'],
      username: json['username'],
      phone: json['phone'],
    );
  }
}
