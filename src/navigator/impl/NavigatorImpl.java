package navigator.impl;

import navigator.Navigator;
import navigator.domain.Route;
import struct.Set;
import struct.impl.ArrayList;
import struct.impl.HashSet;
import struct.impl.LinkedListImpl;
import struct.List;

public class NavigatorImpl implements Navigator {
    private final Set<Route> set;

    public NavigatorImpl() {
        this.set = new HashSet<>();
    }

    @Override
    public void addRoute(Route route) {
        for (Route route1 : set) {
            if (route1.equals(route) || route1.getId().equals(route.getId())) {
                return;
            }
        }
        set.add(route);
    }

    @Override
    public void removeRoute(String routeId) {
        Route route = getRoute(routeId);
        if (route != null) {
            set.remove(route);
        }
    }

    @Override
    public boolean contains(Route route) {
        if (route == null) {
            return false;
        }
        return set.contains(route);
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public Route getRoute(String routeId) {
        LinkedListImpl<Route> route = set.findBucketsByKey(new Route(routeId));
        for (Route r : route) {
            if (routeId.equals(r.getId())) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void chooseRoute(String routeId) {
        Route route = getRoute(routeId);
        if (route != null) {
            route.setPopularity(route.getPopularity() + 1);
        }
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        List<Route> result = new ArrayList<>();
        for (Route r : set) {
            ArrayList<String> local = r.getLocationPoints();
            if(local.getFirst().equals(startPoint) && local.getLast().equals(endPoint)) {
                result.add(r);
            }
        }

        if (result.isEmpty()) {
            return result;
        }

        List<Route> favoriteRoutes = new ArrayList<>();
        List<Route> nonFavoriteRoutes = new ArrayList<>();

        for (Route r : result) {
            if (r.isFavorite()) {
                favoriteRoutes.add(r);
            } else {
                nonFavoriteRoutes.add(r);
            }
        }

        sortRoutes(favoriteRoutes);
        sortRoutes(nonFavoriteRoutes);

        favoriteRoutes.addAll(nonFavoriteRoutes);

        return favoriteRoutes;
    }

    private void sortRoutes(List<Route> routes) {
        routes.sort((route1, route2) -> {
            int points = Integer.compare(route1.getLocationPoints().size(), route2.getLocationPoints().size());
            if (points != 0) {
                return points;
            }
            return Integer.compare(route2.getPopularity(), route1.getPopularity());
        });
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        ArrayList<Route> result = new ArrayList<>();
        for (Route r : set) {
            if(r.isFavorite() && !r.getLocationPoints().getFirst().equals(destinationPoint)
                    && r.getLocationPoints().contains(destinationPoint) ) {
                result.add(r);
            }
        }

        if (result.isEmpty()) {
            return result;
        }

        result.sort((route1, route2) -> {
            int distance = Double.compare(route1.getDistance(), route2.getDistance());
            if (distance != 0) {
                return distance;
            }
            return Integer.compare(route2.getPopularity(), route1.getPopularity());
        });

        return result;
    }

    @Override
    public Iterable<Route> getTop3Routes() {
        List<Route> result = new ArrayList<>();
        for (Route r : set) {
            result.add(r);
        }

        result.sort((route1, route2) -> {
            int popularity = Integer.compare(route2.getPopularity(), route1.getPopularity());
            if (popularity != 0) {
                return popularity;
            }

            int distance = Double.compare(route1.getDistance(), route2.getDistance());
            if (distance != 0) {
                return distance;
            }

            return Integer.compare(route1.getLocationPoints().size(), route2.getLocationPoints().size());

        });

        return result.isEmpty() ? result : result.subList(0, Math.min(result.size(), 5));
    }
}
