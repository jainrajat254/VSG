class RegisterCredentials {
  final String name;
  final String email;
  final String phone;
  final String username;
  final String password;

  RegisterCredentials({
    required this.name,
    required this.email,
    required this.phone,
    required this.username,
    required this.password,
  });

  Map<String, dynamic> toJson() => {
    'name': name,
    'email': email,
    'phone': phone,
    'username': username,
    'password': password,
  };
}
