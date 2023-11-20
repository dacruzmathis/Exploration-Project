package process;

import java.util.ArrayList;
import java.util.List;

import configuration.GameConfiguration;
import map.Intersection;
import map.Map;

public class IntersectionManager {
	private Intersection position;
	private ExplorerManager occupyingExplorer = null;
	private List<Intersection> intersections = new ArrayList<Intersection>();
	private Map map;
	
	public IntersectionManager(Intersection intersection, Map map) {
		this.position = intersection;
		this.map = map;
		initAroundIntersections();
	}
	
	private void initAroundIntersections() {
		if(map.isOnLeftTop(position)) {
			leftTop(position);
		} else if (map.isOnRightTop(position)) {
            rightTop(position);
        } else if (map.isOnLeftBottom(position)) {
            leftBottom(position);
        } else if (map.isOnRightBottom(position)) {
            rightBottom(position);
        } else if (map.isOnTopBorder(position)) {
            topBorder(position);
        } else if (map.isOnBottomBorder(position)) {
            bottomBorder(position);
        } else if (map.isOnLeftBorder(position)) {
            leftBorder(position);
        } else if (map.isOnRightBorder(position)) {
            rightBorder(position);
        } else {
            normal(position);
        }
	}

	private void rightTop(Intersection position2) {
		Intersection position1 = map.getElementPosition(position.getAbscisse() - GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		Intersection position3 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() + GameConfiguration.BLOCK_SIZE);
		intersections.add(position1);
		intersections.add(position3);
	}

	private void normal(Intersection position2) {
		Intersection position1 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() + GameConfiguration.BLOCK_SIZE);
		Intersection position3 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() - GameConfiguration.BLOCK_SIZE);
		Intersection position4 = map.getElementPosition(position.getAbscisse() + GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		Intersection position5 = map.getElementPosition(position.getAbscisse() - GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		intersections.add(position1);
		intersections.add(position3);
		intersections.add(position4);
		intersections.add(position5);
		
	}

	private void rightBorder(Intersection position2) {
		Intersection position1 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() + GameConfiguration.BLOCK_SIZE);
		Intersection position3 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() - GameConfiguration.BLOCK_SIZE);
		Intersection position5 = map.getElementPosition(position.getAbscisse() - GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		intersections.add(position1);
		intersections.add(position3);
		intersections.add(position5);
	}

	private void leftBorder(Intersection position2) {
		Intersection position1 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() + GameConfiguration.BLOCK_SIZE);
		Intersection position3 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() - GameConfiguration.BLOCK_SIZE);
		Intersection position5 = map.getElementPosition(position.getAbscisse() + GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		intersections.add(position1);
		intersections.add(position3);
		intersections.add(position5);
	}

	private void bottomBorder(Intersection position2) {
		Intersection position1 =  map.getElementPosition(position.getAbscisse() - GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		Intersection position3 = map.getElementPosition(position.getAbscisse() + GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		Intersection position5 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() - GameConfiguration.BLOCK_SIZE);
		intersections.add(position1);
		intersections.add(position3);
		intersections.add(position5);
	}

	private void topBorder(Intersection position2) {
		Intersection position1 = map.getElementPosition(position.getAbscisse() - GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		Intersection position3 = map.getElementPosition(position.getAbscisse() + GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		Intersection position5 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() + GameConfiguration.BLOCK_SIZE);
		intersections.add(position1);
		intersections.add(position3);
		intersections.add(position5);
	}

	private void rightBottom(Intersection position2) {
		Intersection position1 = map.getElementPosition(position.getAbscisse() - GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		Intersection position5 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() - GameConfiguration.BLOCK_SIZE);
		intersections.add(position1);
		intersections.add(position5);
	}

	private void leftBottom(Intersection position2) {
		Intersection position1 = map.getElementPosition(position.getAbscisse() + GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		Intersection position5 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() - GameConfiguration.BLOCK_SIZE);
		intersections.add(position1);
		intersections.add(position5);
	}

	private void leftTop(Intersection position2) {
		Intersection position1 = map.getElementPosition(position.getAbscisse() + GameConfiguration.BLOCK_SIZE, position.getOrdonnee());
		Intersection position5 = map.getElementPosition(position.getAbscisse(), position.getOrdonnee() + GameConfiguration.BLOCK_SIZE);
		intersections.add(position1);
		intersections.add(position5);
	}

	public synchronized void enter (ExplorerManager explorerManager) {
		if(occupyingExplorer != null) {
			explorerManager.updatePosition(position);
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
		IntersectionManager previousIntersectionManager = explorerManager.getIntersectionManager();
		previousIntersectionManager.exit();
		explorerManager.setIntersectionManager(this);
		occupyingExplorer = explorerManager;
	}
	
	public synchronized void exit() {
		occupyingExplorer = null;
		
		notify();
	}
	
	public boolean isFree() {
		return occupyingExplorer == null;
	}
	
	public int getAbscisse() {
		return position.getAbscisse();
	}
	
	public int getOrdonnee() {
		return position.getOrdonnee();
	}
	
	public List<Intersection> getIntersections() {
		return intersections;
	}
}
