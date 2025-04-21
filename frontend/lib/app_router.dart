import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:vsg/screens/home_screen.dart';
import 'package:vsg/screens/login_screen.dart';
import 'package:vsg/screens/register_screen.dart';
import 'package:vsg/shared_preferences.dart';

final GoRouter appRouter = GoRouter(
  initialLocation: '/login',
  redirect: (BuildContext context, GoRouterState state) async {
    final loggedIn = await UserPreferences.isUserLoggedIn();
    final isLoggingIn = state.uri.path == '/login';
    final isRegistering = state.uri.path == '/register';

    if (!loggedIn && !(isLoggingIn || isRegistering)) {
      return '/login';
    }

    if (loggedIn && (isLoggingIn || isRegistering)) {
      return '/home';
    }

    return null;
  },
  routes: [
    GoRoute(path: '/login', builder: (context, state) => const LoginScreen()),
    GoRoute(path: '/register', builder: (context, state) => const RegisterScreen()),
    GoRoute(path: '/home', builder: (context, state) => const HomeScreen()),
    GoRoute(path: '/group', builder: (context, state) => const HomeScreen()),
    GoRoute(path: '/session', builder: (context, state) => const HomeScreen()),
    GoRoute(path: '/profile', builder: (context, state) => const HomeScreen()),
  ],
  errorBuilder: (context, state) => Scaffold(
    body: Center(child: Text('Route not found: ${state.uri.path}')),
  ),
);
