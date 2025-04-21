import 'package:shared_preferences/shared_preferences.dart';
import 'package:vsg/model/login_response.dart';

class UserPreferences {
  static const String _userId = 'user_id';
  static const String _name = 'name';
  static const String _username = 'username';
  static const String _token = 'token';
  static const String _email = 'email';
  static const String _phone = 'phone';

  static Future<bool> isUserLoggedIn() async {
    final token = await UserPreferences.getToken();
    return token != null;
  }

  static Future<String?> getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_token);
  }

  static Future<String?> getName() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_name);
  }

  static Future<String?> getUsername() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_username);
  }

  static Future<String?> getId() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_userId);
  }

  static Future<String?> getEmail() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_email);
  }

  static Future<String?> getPhone() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_phone);
  }

  static Future<void> saveUser({
    required LoginResponse response
  }) async {
    final prefs = await SharedPreferences.getInstance();
    await Future.wait([
      prefs.setString(_userId, response.id),
      prefs.setString(_token, response.token),
      prefs.setString(_name, response.name),
      prefs.setString(_phone, response.phone),
      prefs.setString(_email, response.email),
      prefs.setString(_username, response.username),
    ]);
  }

  static Future<void> clearUser() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_userId);
    await prefs.remove(_username);
    await prefs.remove(_name);
    await prefs.remove(_phone);
    await prefs.remove(_email);
    await prefs.remove(_token);
  }
}
