import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:vsg/model/login_credentials.dart';
import 'package:vsg/model/register_credentials.dart';
import 'package:vsg/model/login_response.dart';

class AuthService {
  final String baseUrl = "http://10.0.2.2:8080";

  Future<LoginResponse?> registerUser(RegisterCredentials credentials) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/register'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(credentials.toJson()),
    );
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return LoginResponse.fromJson(json);
    } else {
      print('Register failed: ${response.body}');
      return null;
    }
  }

  Future<LoginResponse?> loginUser(LoginCredentials credentials) async {
    final response = await http.post(
      Uri.parse('$baseUrl/auth/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(credentials.toJson()),
    );
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return LoginResponse.fromJson(json);
    } else {
      print('Register failed: ${response.body}');
      return null;
    }
  }
}
