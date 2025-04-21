import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:vsg/customs/custom_bottom_bar.dart';
import 'package:vsg/customs/custom_fab.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  int _getSelectedIndex(String location) {
    if (location.startsWith('/group')) return 1;
    if (location.startsWith('/session')) return 2;
    if (location.startsWith('/profile')) return 3;
    return 0;
  }

  void _onFabPressed(BuildContext context) {
    final location = GoRouter.of(context).routerDelegate.currentConfiguration.uri.toString();
    if (location.startsWith('/group')) {
      // Handle group FAB action
    } else if (location.startsWith('/session')) {
      // Handle session FAB action
    } else {
      // Handle default chat FAB action
    }
  }

  @override
  Widget build(BuildContext context) {
    final location = GoRouter.of(context).routerDelegate.currentConfiguration.uri.toString();
    final selectedIndex = _getSelectedIndex(location);

    return Scaffold(
      appBar: AppBar(centerTitle: true),
      body: const SizedBox.shrink(),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      floatingActionButton: CustomFAB(onPressed: () => _onFabPressed(context)),
      bottomNavigationBar: CustomBottomBar(selectedIndex: selectedIndex),
    );
  }
}
