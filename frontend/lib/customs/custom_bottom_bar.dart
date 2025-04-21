import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class CustomBottomBar extends StatelessWidget {
  final int selectedIndex;

  const CustomBottomBar({
    super.key,
    required this.selectedIndex,
  });

  void _navigateTo(BuildContext context, int index) {
    switch (index) {
      case 0:
        context.go('/home');
        break;
      case 1:
        context.go('/group');
        break;
      case 2:
        context.go('/session');
        break;
      case 3:
        context.go('/profile');
        break;
    }
  }

  @override
  Widget build(BuildContext context) {
    return BottomAppBar(
      shape: const CircularNotchedRectangle(),
      notchMargin: 5.0,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: <Widget>[
          IconButton(
            icon: Icon(
              Icons.chat,
              color: selectedIndex == 0 ? Colors.blue : Colors.grey,
            ),
            onPressed: () => _navigateTo(context, 0),
          ),
          IconButton(
            icon: Icon(
              Icons.group,
              color: selectedIndex == 1 ? Colors.blue : Colors.grey,
            ),
            onPressed: () => _navigateTo(context, 1),
          ),
          const SizedBox(width: 40), // space for FAB
          IconButton(
            icon: Icon(
              Icons.meeting_room,
              color: selectedIndex == 2 ? Colors.blue : Colors.grey,
            ),
            onPressed: () => _navigateTo(context, 2),
          ),
          IconButton(
            icon: Icon(
              Icons.person,
              color: selectedIndex == 3 ? Colors.blue : Colors.grey,
            ),
            onPressed: () => _navigateTo(context, 3),
          ),
        ],
      ),
    );
  }
}
