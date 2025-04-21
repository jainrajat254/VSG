import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:vsg/auth/auth_service.dart';
import 'package:vsg/customs/custom_text_field.dart';
import 'package:vsg/model/login_credentials.dart';
import 'package:vsg/model/login_response.dart';
import 'package:vsg/shared_preferences.dart';
import '../customs/auth_redirect.dart';
import '../customs/custom_button.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _obscureText = true;
  bool _isLoading = false;

  final authService = AuthService();

  void _login() async {
    String username = _usernameController.text.trim();
    String password = _passwordController.text.trim();

    if (username.isEmpty || password.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Please enter both username and password.')),
      );
      return;
    }

    LoginCredentials credentials = LoginCredentials(
      username: username,
      password: password,
    );

    setState(() {
      _isLoading = true;
    });

    try {
      LoginResponse? response = await authService.loginUser(credentials);

      if (response != null) {
        await UserPreferences.saveUser(response: response);
        context.go('/home');
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Welcome, ${response.name}!')),
        );
        print(response.id);
        print(response.username);
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Login failed. Please try again.')),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: ${e.toString()}')),
      );
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Center(child: Text('Login'))),
      body: LayoutBuilder(
        builder: (context, constraints) {
          return SingleChildScrollView(
            child: ConstrainedBox(
              constraints: BoxConstraints(minHeight: constraints.maxHeight),
              child: Padding(
                padding: const EdgeInsets.all(24),
                child: Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      const Text(
                        'Welcome back',
                        style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
                      ),
                      const SizedBox(height: 24),

                      CustomTextField(
                        controller: _usernameController,
                        label: "Username",
                        icon: Icons.person,
                      ),
                      const SizedBox(height: 16),

                      CustomTextField(
                        controller: _passwordController,
                        label: "Password",
                        icon: Icons.lock,
                        obscureText: _obscureText,
                        onSuffixIconPressed: () {
                          setState(() {
                            _obscureText = !_obscureText;
                          });
                        },
                      ),
                      const SizedBox(height: 48),

                      Stack(
                        alignment: Alignment.center,
                        children: [
                          CustomButton(
                            label: "Login",
                            onPressed: () {
                              _login();
                            },
                            icon: Icons.security,
                          ),
                          if (_isLoading) const CircularProgressIndicator(),
                        ],
                      ),
                      const SizedBox(height: 32),

                      AuthRedirect(
                        text: "Don't have an account? ",
                        clickableText: "Register here",
                        onPressed: () => context.push('/register'),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}
